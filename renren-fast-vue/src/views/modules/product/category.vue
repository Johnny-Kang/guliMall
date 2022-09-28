<template>
  <el-tree :data="data" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
</template>

<script>
  export default {
    data() {
      return {
        data: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      };
    },
    created(){
      this.getDataList()
    },
    methods: {
      handleNodeClick(data) {
        console.log(data);
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/product/category/list/tree'),
          method: 'get',
        }).then(({data}) => {
          console.log(data);
          this.dataListLoading = false
        })
      },
    }
  };
</script>

<style scoped>

</style>
