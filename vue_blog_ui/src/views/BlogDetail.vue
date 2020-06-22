<template>
  <div>
    <Header></Header>
    <el-card class="m-blog">
      <el-button-group class="m-btn">
        <el-button size="medium" type="info" icon="el-icon-d-arrow-left" @click="goBack">返回</el-button>
        <el-button v-show="ownBlog" size="medium" type="primary" icon="el-icon-edit" @click="editBlog">编辑</el-button>
        <el-button v-show="ownBlog" size="medium" type="danger" icon="el-icon-delete" @click="deleteBlog">删除</el-button>
      </el-button-group>
      <h2>{{blog.title}}</h2>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </el-card>
  </div>
</template>

<script>
  import Header from "../components/Header";
  import "github-markdown-css/github-markdown.css"// 然后添加样式markdown-body
  import MarkdownIt from "markdown-it"

  export default {
    name: "BlogDetail",
    components: {Header},
    data() {
      return {
        blog: {
          id: '',
          title: '',
          content: ''
        },
        ownBlog: false
      }
    },
    methods: {
      editBlog() {
        this.$router.push("/blog/" + this.blog.id + "/edit")
      },
      deleteBlog() {
        this.$confirm('是否删除该博文?', '提示', {
          type: "warning",
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(() => {
          this.$axios.delete("/blog/" + this.blog.id, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          })
          this.$message.success({
            message: '删除成功',
            duration: 800,
            onClose: () => {
              this.$router.push("/blogs")
            }
          });
        }).catch(() => {
          this.$message.info('取消删除');
        })
      },
      goBack() {
        this.$router.go(-1)
      },
      fetchData() {
        const blogId = this.$route.params.blogId
        if (blogId) {
          this.$axios.get("/blog/" + blogId).then(res => {
            const blog = res.data.data
            this.blog.id = blog.id
            this.blog.title = blog.title

            // const MarkdownIt = require("markdown-it")
            this.blog.content = new MarkdownIt().render(blog.content)

            // 展示操作按钮
            if (this.$store.getters.getUserInfo) {
              this.ownBlog = (blog.userId === this.$store.getters.getUserInfo.id)
            }
          })
        }
      }
    },
    created() {
      this.fetchData()
    }
  }
</script>

<style scoped>
  .m-blog {
    min-height: 700px;
  }

  .m-btn {
    float: right;
    margin: 10px 0
  }
</style>