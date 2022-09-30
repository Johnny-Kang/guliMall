<template>
  <div>
    <el-tree
      :data="dataList"
      :props="defaultProps"
      show-checkbox
      :expand-on-click-node="false"
      node-key="catId"
      :default-expanded-keys="expandKey"
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
      title="新增分类"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form :model="listQuery">
        <el-form-item label="分类名称">
          <el-input v-model="listQuery.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMenu">确 定</el-button>
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
        sort: 0
      },
      dataList: [],
      expandKey: [],
      dialogVisible:false,
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },
  created() {
    this.getDataList();
  },
  methods: {
    handleClose(){
      this.dialogVisible = false
    },
    append(data) {
      this.dialogVisible = true
      this.listQuery.parentCid = data.catId
      this.listQuery.catLevel = data.catLevel*1+1;
    },
    /**
     * 添加三级分类方法
     */
    addMenu(){
      this.$http({
      url: this.$http.adornUrl('/product/category/save'),
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
      })
        .then(() => {
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
