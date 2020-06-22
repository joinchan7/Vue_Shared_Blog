<template>
  <div>
    <Header></Header>
    <div class="block">
      <el-timeline>
        <el-timeline-item v-for="blog in blogs" :timestamp="blog.created" placement="top">
          <el-card>
              <router-link class="m-link" :to="{name:'BlogDetail',params:{blogId:blog.id}}">
                <el-link type="primary">
                  <i class="el-icon-view"></i>
                  {{blog.title}}
                </el-link>
              </router-link>
            <p>{{blog.description}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>


      <el-pagination class="m-pagination"
                     hide-on-single-page
                     background
                     layout="prev, pager, next"
                     :current-page="currentPage"
                     :page-size="pageSize"
                     :total="total"
                     @current-change="page">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import Header from "../components/Header";

  export default {
    name: "Blogs",
    components: {Header},
    data() {
      return {
        blogs: {},
        currentPage: 1,
        total: 0,
        pageSize: 5
      }
    },
    methods: {
      page(currentPage = 1) {
        this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {
          // console.log(res)
          this.blogs = res.data.data.records
          this.currentPage = res.data.data.current
          this.total = res.data.data.total
          this.pageSize = res.data.data.size
        })
      }
    },
    created() {
      this.page()
    }
  }
</script>

<style scoped>
  .m-pagination {
    text-align: center;
  }

  .m-link {
    text-decoration: none;
  }
</style>