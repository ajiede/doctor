<template>
  <view class="content">
    <el-form label-width="85px">

      <!-- 手动输入UserId -->
      <!--<el-form-item label="接收用户Id">-->
      <!--  <el-input v-model="textMessage.receiver"></el-input>-->
      <!--</el-form-item>-->

      <!-- 选择在线用户 -->
      <el-form-item label="聊天用户">
        <el-select v-model="textMessage.receiver"
                   placeholder="请选择聊天用户"
                   @change="selectOnlineUser">
          <el-option v-for="(user,index) in onlineUserList"
                     :key="index"
                     :label="user.username"
                     :value="user.userId"></el-option>
        </el-select>
      </el-form-item>

      <!-- 聊天内容区域 -->
      <el-form-item label="聊天内容">
        <el-input v-model="textMessage.msg"></el-input>
      </el-form-item>

      <!-- 发送按钮 -->
      <el-form-item>
        <el-button type="primary" @click="sendMsg">发送</el-button>
        <el-button type="primary" @click="getOnlineUserList">刷新在线用户</el-button>
      </el-form-item>
    </el-form>

    <!-- 历史记录 -->
    <view class="historyContainer">
      <el-row class="infinite-list" style="overflow:hidden" v-for="history in historyChatList">
        <el-col class="infinite-list-item"
                :span="4"
                :style="{ backgroundColor: history.bgc,float:history.float}"
                :xs="4" :sm="6" :md="8" :lg="9" :xl="11"
        >
          {{ history.username }}：{{ history.msg }}
          <!--{{ history.msg }}-->
        </el-col>
      </el-row>
    </view>

  </view>
</template>

<script>
import axios from 'axios';

export default {

  data() {
    return {
      //websocket操作对象
      webSocket: null,
      //当前用户Id
      userId: '',
      //当前用户要发送的消息
      msg: '发送的消息',
      //要发送消息的用户
      receiver: '',
      //在线用户列表
      onlineUserList: [
        // {
        //   'userId': '4HdMcp',
        //   'username': 'Edge'
        // },
      ],
      //历史消息
      historyChatList: [
        // {
        //   "userId": '',
        //   'msg': '',
        //   'bgc': 'white',
        // }
      ],
      //TextMessage
      textMessage: {userId: '', receiver: '', msg: '', type: 'TEXT'},
      //RegisterMessage
      registerMessage: {userId: '', type: 'REGISTER'},
      //HeaterMessage
      heartMessage: {userId: '', type: 'HEARTBEAT'}

    }
  },
  created() {
    // this.webSocket = new WebSocket("ws://localhost:19999/websocket")
    this.webSocket = new WebSocket("ws://127.0.0.1:19999/websocket")
    this.initWebSocket()
    //随机获取一个UserId
    this.userId = this.generateRandomString(6)
    //设置注册包、心跳包、TextMessage的UserId
    this.registerMessage.userId = this.heartMessage.userId = this.textMessage.userId = this.userId
    console.log(`当前UserId：${this.userId}`);
    console.log("注册包：{} --- 心跳包：{}", this.registerMessage, this.heartMessage)

    uni.setNavigationBarTitle({
      title: `您的UserId：${this.userId}`
    })
  },

  methods: {
    onOpen() {
      //向服务器发送注册消息
      this.registerMessage.userId = this.userId
      this.webSocket.send(JSON.stringify(this.registerMessage))
      console.log('WebSocket连接成功，状态码：', this.webSocket.readyState)

      //获取所有的在线用户
      this.getOnlineUserList()


      //发送心跳包
      setInterval(() => {
        this.webSocket.send(JSON.stringify(this.heartMessage))
        console.log('心跳')
      }, 3000)
    },
    /**
     * 将发送的消息存入历史记录
     * @param userId
     * @param msg
     * @param type
     */
    pushHistory({userId, msg, type}) {

      //如果type不是TEXT类型，不用存入历史记录
      if (type !== 'TEXT') {
        return
      }

      // debugger
      let history = {
        userId,
        username: '',
        msg,
        bgc: '',
        float: ''
      }
      if (history.userId === this.textMessage.userId) {
        history.bgc = '#95ec69'
        history.username = `发给 ${this.textMessage.userId}`
        history.float = 'right'
      } else {
        history.bgc = 'white'
        history.username = history.userId
        history.float = 'left'
      }
      this.historyChatList.push(history)
      this.load()
    },
    onMessage(event) {
      console.log('WebSocket收到消息 --> ', event.data);

      //提醒用户上线
      if (event.data.includes("上线")) {
        this.$message({
          message: event.data,
          type: 'success'
        });
        //刷新在线用户数据
        this.getOnlineUserList()
        return
      }

      //提醒用户下线
      if (event.data.includes("下线")) {
        this.$message({
          message: event.data,
          type: 'error'
        });
        //刷新在线用户数据
        this.getOnlineUserList()
        return
      }

      //发送了TextMessage之后服务端的响应 --> "userId：消息"
      let datas = event.data.split("：");
      this.pushHistory({
        userId: datas[0],
        msg: datas[1],
        type: 'TEXT'
      });

    },
    onError() {
      console.log('WebSocket连接错误，状态码：', this.webSocket.readyState)
    },
    onClose() {
      console.log('WebSocket连接关闭，状态码：', this.webSocket.readyState)
    },
    initWebSocket() {
      // 连接成功
      this.webSocket.onopen = this.onOpen
      // 收到消息的回调
      this.webSocket.onmessage = this.onMessage;
      // 连接错误
      this.webSocket.onerror = this.onError;
      // 连接关闭的回调
      this.webSocket.onclose = this.onClose;
    },
    //发送消息
    sendMsg() {
      let msg = JSON.stringify(this.textMessage)
      this.webSocket.send(msg)
      this.pushHistory(this.textMessage)
      // console.log(msg)
    },
    //选择聊天用户
    selectOnlineUser(userId) {
      this.textMessage.receiver = userId
    },
    //获取所有的在线用户
    getOnlineUserList() {
      let _onlineUserList = this.onlineUserList
      axios.get("http://127.0.0.1:9999/user/online/list")
          .then(({data}) => {
            this.onlineUserList = data.data
            //不显示当前UserId，只显示别的在线的UserId
            for (var i = 0; i < this.onlineUserList.length; i++) {
              // debugger
              if (this.onlineUserList[i].userId === this.userId) {
                this.onlineUserList.splice(i, 1)
                break
              }
            }
          })
    },

    scrollToBottom() {
      let container = document.querySelector('.historyContainer')
      // let container = this.$refs.historyContainer
      container.scrollTop = container.scrollHeight;
    },
    load() {
      this.$nextTick(() => {
        this.scrollToBottom()
      })
    },
    //生成一个指定长度的字符串
    generateRandomString(length) {
      var result = '';
      var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      for (var i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      return result;
    }
  }
}
</script>

<style>
.historyContainer {
  height: 500px;
  overflow: auto;
  background-color: #f5f5f5;
}

.infinite-list-item {
  border-radius: 15px;
  margin: 10px 0;
  padding: 10px;
  display: inline-block;
  list-style: none;
  max-width: 400px;

}
</style>
