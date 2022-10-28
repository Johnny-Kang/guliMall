<template>
  <div>
    <el-switch v-model="draggable" active-text="开启拖拽" inactive-text="关闭拖拽"></el-switch>
    <el-button v-if="draggable" @click="batchSave">批量保存</el-button>
    <el-button type="danger" @click="batchDelete">批量删除</el-button>
    <el-tree
      :data="dataList"
      :props="defaultProps"
      show-checkbox
      :expand-on-click-node="false"
      node-key="catId"
      :default-expanded-keys="expandKey"
      :draggable="true"
      :allow-drop="allowDrop"
      @node-drop="handleDrop"
    >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
            v-if="node.level <= 2"
            type="text"
            size="mini"
            @click="() => append(data)"
          >
            Append
          </el-button>

          <el-button
              type="text"
              size="mini"
              @click="() => update(data)"
            >
            update
          </el-button>

          <el-button
            v-if="data.children.length == 0"
            type="text"
            size="mini"
            @click="() => remove(node, data)"
          >
            Delete
          </el-button>
        </span>
      </span>
    </el-tree>

    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form :model="listQuery">
        <el-form-item label="分类名称">
          <el-input v-model="listQuery.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标">
          <el-input v-model="listQuery.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="计数单位">
          <el-input v-model="listQuery.productUnit" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAndUpdateMenu">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      listQuery: {
        name: "",
        parentCid: 0,
        catLevel: 0,
        showStatus: 1,
        sort: 0,
        catId:null,
        productCount:0,
        icon:null,
        productUnit:null,
      },
      title:'',
      dataList: [],
      expandKey: [],
      dialogVisible:false,
      defaultProps: {
        children: "children",
        label: "name"
      },
      maxLevel: 0,
      pCids: [],
      updateNodes: [],
      draggable:false
    };
  },
  created() {
    this.getDataList();
  },
  methods: {
    batchSave(){
      this.$http({
        url: this.$http.adornUrl(`/update/sort`),
        method: 'post',
        data: this.$http.adornData(this.updateNodes, false)
      }).then(({ data }) => {
        this.$message({
          type: "success",
          message: "修改成功!"
        });
        this.getDataList();
        this.expandKey = this.pCids;
      })
    },
    batchDelete(){

    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log('tree drop: ', draggingNode, dropNode, dropType);

      let pCid = 0
      let siblings = null
      //当前node的最新父节点id
      if (dropType === 'inner'){
        pCid = dropNode.data.catId
        siblings = dropNode.childNodes
      }else{
        pCid = dropNode.data.parentCid
        siblings = dropNode.parent.childNodes
      }
      this.pCids.push(pCid)

      //2、当前拖拽节点的最新顺序，
      for (let i = 0; i < siblings.length; i++) {
        if (siblings[i].data.catId == draggingNode.data.catId) {
          //如果遍历的是当前正在拖拽的节点
          let catLevel = draggingNode.level;
          if (siblings[i].level != draggingNode.level) {
            //当前节点的层级发生变化
            catLevel = siblings[i].level;
            //修改他子节点的层级
            this.updateChildNodeLevel(siblings[i]);
          }
          this.updateNodes.push({
            catId: siblings[i].data.catId,
            sort: i,
            parentCid: pCid,
            catLevel: catLevel
          });
        } else {
          this.updateNodes.push({ catId: siblings[i].data.catId, sort: i });
        }
      }
      //当前拖拽节点的最新层级

    },
    //修改子节点的层级
    updateChildNodeLevel(node) {
      if (node.childNodes.length > 0) {
        for (let i = 0; i < node.childNodes.length; i++) {
          this.updateNodes.push({
            catId: node.childNodes[i].data.catId,
            catLevel: node.childNodes[i].level
          });
          this.updateChildNodeLevel(node.childNodes[i]);
        }
      }
    },
    allowDrop(draggingNode, dropNode, type) {
      this.countNodeLevel(draggingNode.data)
      let deep = (this.maxLevel - draggingNode.data.catLevel) + 1
      if (type === 'inner'){
        return (deep + dropNode.level) <= 3
      }else {
        return (deep +dropNode.parent.level) <= 3
      }
    },
    countNodeLevel(node){
      if (node.children != null && node.children.length > 0){
        node.children.forEach(item => {
          if (item.catLevel > this.maxLevel){
            this.maxLevel = item.catLevel
          }
          this.countNodeLevel(item)
        })
      }
    },
    handleClose(){
      this.dialogVisible = false
      Object.assign(this.listQuery,this.$options.data().listQuery)
      this.updateNodes = []
      this.maxLevel = 0
    },
    append(data) {
      this.title = '添加分类'
      this.dialogVisible = true
      this.listQuery.parentCid = data.catId
      this.listQuery.catLevel = data.catLevel*1+1;
    },
    update(data){
      this.title = '修改分类'
      this.dialogVisible = true
      this.$nextTick(() => {
        this.listQuery = {...data}
      })
    },
    /**
     * 添加三级分类方法
     */
    addAndUpdateMenu(){
      this.$http({
      url: this.$http.adornUrl(`/product/category/${this.listQuery.catId == null?'save':'update'}`),
      method: 'post',
      data: this.$http.adornData(this.listQuery, false)
      }).then(({ data }) => {
        this.$message({
          type: "success",
          message: "新增成功!"
        });
        this.getDataList();
        this.dialogVisible = false
        this.expandKey = [this.listQuery.parentCid];
      })
    },
    remove(node, data) {
      let ids = [data.catId];

      this.$confirm(`是否删除【${data.name}】菜单`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
          this.$http({
            url: this.$http.adornUrl("/product/category/delete"),
            method: "post",
            data: this.$http.adornData(ids, false)
          }).then(({ data }) => {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getDataList();
            this.expandKey = [node.parent.data.catId];
          });
        })
        .catch(() => {});
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({ data }) => {
        this.dataList = data.data;
        this.dataListLoading = false;
      });
    }
  }
};
</script>

<style scoped></style>
