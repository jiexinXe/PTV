<template>
  <el-dialog
    :title="!dataForm.whid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="type">
      <el-input v-model="dataForm.type" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="maxcontent">
      <el-input v-model="dataForm.maxcontent" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="nowcontent">
      <el-input v-model="dataForm.nowcontent" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          whid: 0,
          type: '',
          maxcontent: '',
          nowcontent: ''
        },
        dataRule: {
          type: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          maxcontent: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          nowcontent: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.whid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.whid) {
            this.$http({
              url: this.$http.adornUrl(`//warehouse/info/${this.dataForm.whid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.type = data.warehouse.type
                this.dataForm.maxcontent = data.warehouse.maxcontent
                this.dataForm.nowcontent = data.warehouse.nowcontent
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`//warehouse/${!this.dataForm.whid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'whid': this.dataForm.whid || undefined,
                'type': this.dataForm.type,
                'maxcontent': this.dataForm.maxcontent,
                'nowcontent': this.dataForm.nowcontent
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
