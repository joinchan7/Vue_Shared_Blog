<template>
  <div>
    <Header></Header>

    <div class="m-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="标题" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>
        <el-form-item label="摘要" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <mavon-editor v-model="ruleForm.content"></mavon-editor>
          <el-input type="textarea" v-model="ruleForm.content"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
          <el-button @click="goBack('ruleForm')" type="info">返回</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import Header from "../components/Header";

  export default {
    name: "BlogEdit",
    components: {Header},
    data() {
      return {
        ruleForm: {
          id: '',
          title: '',
          description: '',
          content: ''
        },
        rules: {
          title: [
            {required: true, message: '请输入标题', trigger: 'blur'},
            {min: 1, max: 25, message: '长度在 1 到 25 个字符', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '请输入摘要', trigger: 'blur'}
          ],
          content: [
            {required: true, message: '请输入内容', trigger: 'blur'}
          ]
        },
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
            if (valid) {
              this.$confirm('是否保存该博文?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                this.$axios.post("/blog/edit", this.ruleForm, {
                  headers: {
                    "Authorization": localStorage.getItem("token")
                  }
                })
                this.$message.success({
                  message: '保存成功',
                  onClose: () => {
                    this.$router.push("/blogs")
                  }
                });
              }).catch(() => {
                this.$message.info({
                  message: '停留在当前页面'
                });
              })
            } else {
              // console.log('error submit!!');
              return false;
            }
          }
        )
        ;
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      goBack(formName) {
        if (this.ruleForm.title || this.ruleForm.description || this.ruleForm.content) {
          // 检测到页面变化
          this.$confirm('检测到未保存的内容，是否在离开页面前保存修改？', '确认信息', {
            distinguishCancelAndClose: true,
            confirmButtonText: '保存',
            cancelButtonText: '放弃修改'
          }).then(() => {
            this.save(formName)
          }).catch(action => {
            if (action === 'cancel') {
              this.$router.go(-1)
            }
            this.$message({
              type: 'info',
              message: action === 'cancel'
                ? '放弃保存并离开页面'
                : '停留在当前页面'
            })
          });
        } else {
          this.$router.go(-1)
        }


      },
      save(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post("/blog/edit", this.ruleForm, {
              headers: {
                "Authorization": localStorage.getItem("token")
              }
            }).then(() => {
              this.$message.success({
                message: '保存成功',
                duration: 1000,
                onClose: () => {
                  this.$router.push("/blogs")
                }
              })
            })
          } else {
            // console.log('error submit!!');
            return false;
          }
        });
      },
      fetchData() {
        const blogId = this.$route.params.blogId
        if (blogId) {
          this.$axios.get("/blog/" + blogId).then(res => {
            // console.log(res)
            const blog = res.data.data
            this.ruleForm.id = blog.id
            this.ruleForm.title = blog.title
            this.ruleForm.description = blog.description
            this.ruleForm.content = blog.content
          }).catch(() => {
            // 找不到博文,跳转到主页
            this.$router.push("/blogs")
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
  .m-content {
    text-align: center;
  }

  .m-back {
    margin: 40px 15px;
  }
</style>