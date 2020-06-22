<template>
  <el-container>
    <el-header>
      <img class="m-logo" src="../assets/logo.png" alt="logo">
    </el-header>
    <el-main>
      <el-card class="box-card m-card">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="m-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" @keyup.enter.native="submitForm('ruleForm')">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        ruleForm: {
          username: 'markerhub',
          password: '111111'
        },
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {min: 3, max: 15, message: '长度在 3 到 12 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post('/login', this.ruleForm).then(res => {
              const token = res.headers['authorization'];
              const userInfo = res.data.data;

              // 放到 localStorage 中
              this.$store.commit("SET_TOKEN", token);
              this.$store.commit("SET_USERINFO", userInfo);
              this.$router.push("/blogs")
              this.$message.success({
                message: '登录成功',
                duration: 1000
              })
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
  .el-header, .el-footer {
    background-color: #d2d9e2;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    /*background-color: #E9EEF3;*/
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .m-logo {
    height: 85%;
    margin-top: 5px;
  }

  .m-ruleForm {
    max-width: 500px;
    margin: 60px auto;
  }

  .m-card {
    margin-top: 100px;
  }
</style>