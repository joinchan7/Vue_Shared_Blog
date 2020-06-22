<template>
  <div class="m-content">
    <el-backtop :visibility-height="100"></el-backtop>
    <h3>欢迎来到 VUE_BLOG 共享博客</h3>
    <div class="block">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <div>{{user.username}}</div>
    </div>

    <div class="m-action">
      <el-link type="info" href="/blogs"><i class="el-icon-house"></i> 主页</el-link>
      <el-divider direction="vertical"></el-divider>
      <el-link type="success" href="/blog/add"><i class="el-icon-edit"></i> 发表博客</el-link>
      <el-divider direction="vertical"></el-divider>
      <el-link v-show="!hasLogin" type="primary" href="/login"><i class="el-icon-user"></i> 登录</el-link>
      <el-link v-show="hasLogin" type="danger" @click="logout"><i class="el-icon-truck"></i> 退出</el-link>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {
        user: {
          username: "请先登录",
          avatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
        },
        hasLogin: false
      }
    },
    methods: {
      logout() {
        // 后端 jwt 无状态,无需请求
        this.$router.push("/login")
        this.$axios.get("/logout", {
          headers: {
            "Authorization": localStorage.getItem("token")
          }
        }).then(() => {
          this.$store.commit("REMOVE_INFO")
        })
        this.$message.warning({
          message: '退出成功',
          duration: 1000
        })
      },
      fetchInfo() {
        // console.log(this.$store.getters.getUserInfo.username)
        const _token = this.$store.getters.getToken
        if (_token) {
          this.$axios.post("/info", _token, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            this.$store.commit("SET_USERINFO", res.data.data);
            if (this.$store.getters.getUserInfo.username) {
              this.user.username = this.$store.getters.getUserInfo.username
              this.user.avatar = this.$store.getters.getUserInfo.avatar
              this.hasLogin = true
            }
          })
        }
      }
    },
    created() {
      this.fetchInfo();
    }
  }
</script>

<style scoped>
  .m-content {
    text-align: center;
  }

  .m-action {
    margin: 10px 0;
  }
</style>