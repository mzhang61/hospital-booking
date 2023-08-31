<template>
  <div class="app-container">
    add hospital
    <el-form label-width="120px">
      <el-form-item label="hospital name">
        <el-input v-model="hospitalSet.hosname" />
      </el-form-item>
      <el-form-item label="hospital code">
        <el-input v-model="hospitalSet.hoscode" />
      </el-form-item>
      <el-form-item label="api basic route">
        <el-input v-model="hospitalSet.apiUrl" />
      </el-form-item>
      <el-form-item label="contact name">
        <el-input v-model="hospitalSet.contactsName" />
      </el-form-item>
      <el-form-item label="contact number">
        <el-input v-model="hospitalSet.contactsPhone" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">save</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import hospset from "@/api/hospset";
export default {
  data() {
    return {
      hospitalSet: {}
    };
  },
  // excuted before page rendering
  created() {
    // get router id
    // revoke api to get hospital info
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id
      this.getHostSet(id)
    } else {
      // clean data
      this.hospitalSet = {}
    }
  },
  methods: {
    // search by id
    getHostSet(id) {
      hospset.getHospSet(id).then(response => {
        this.hospitalSet = response.data
      })
    },

    // update
    update() {
      hospset.updateHospSet(this.hospitalSet).then(response => {
        this.$message({
          type: "success",
          message: "add successfully!"
        })
        // redirect the page by using router
        this.$router.push({ path: "/hospSet/list" })
      })
    },
    // add or supdate
    saveOrUpdate() {
      if(!this.hospitalSet.id) {
        this.save();
      } else {
        this.update()
      }
    }
  }
};
</script>
