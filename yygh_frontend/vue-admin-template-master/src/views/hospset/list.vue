<template>
  <div class="app-container">
    Set hospital List
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.hosname" placeholder="hospital name" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.hoscode" placeholder="hospital code" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >search</el-button
      >
    </el-form>
    <!-- tool bar -->
    <div>
      <el-button type="danger" size="mini" @click="removeRows()"
        >batch delete</el-button
      >
    </div>
    <!-- banner-->
    <el-table
      :data="list"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" width="130" label="Serial number" />
      <el-table-column prop="hosname" label="hospital name" />
      <el-table-column prop="hoscode" label="hospital code" />
      <el-table-column prop="apiUrl" label="api basic route" width="200" />
      <el-table-column prop="contactsName" label="contact nmae" />
      <el-table-column prop="contactsPhone" label="contact phone number" />
      <el-table-column label="condition" width="100">
        <template slot-scope="scope">
          {{ scope.row.status === 1 ? "available" : "not available" }}
        </template>
      </el-table-column>

      <el-table-column label="manipulation" width="280" align="center">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
            >delete</el-button
          >
          <el-button
            v-if="scope.row.status == 1"
            type="primary"
            size="mini"
            icon="el-icon-delete"
            @click="lockHostSet(scope.row.id, 0)"
            >lock</el-button
          >
          <el-button
            v-if="scope.row.status == 0"
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="lockHostSet(scope.row.id, 1)"
            >unlock</el-button
          >
          <router-link :to="'/hospSet/edit/' + scope.row.id">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
            ></el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <!-- Paging -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
import hospset from "@/api/hospset";

export default {
  data() {
    return {
      current: 1,
      limit: 3,
      searchObj: {},
      list: [],
      total: 0
    };
  },
  created() {
    this.getList()
  },
  methods: {
    // lock and unlock
    lockHostSet(id, status) {
      hospset.lockHospSet(id, status).then(response => {
        // refresh
        this.getList()
      });
    },
    handleSelectionChange(selection) {
      this.multipleSelection = selection;
    },
    // batch remove
    removeRows() {
      this.$confirm(
        "the operation will delete the file permenently, do you want to continue?",
        "message",
        {
          confirmButtonText: "confirm",
          cancelButtonText: "cancel",
          type: "warning"
        }
      )
        .then(() => {
          var idList = [];
          for (var i = 0; i < this.multipleSelection.length; i++) {
            var obj = this.multipleSelection[i];
            var id = obj.id;
            idList.push(id);
          }
          hospset.batchRemoveHospSet(idList).then(response => {
            // show message
            this.$message({
              type: "success",
              message: "deleted sucessfully!"
            });
            // refresh page
            this.getList(1);
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "delete cancelled"
          });
        });
    },
    getList(page = 1) {
      this.current = page;
      hospset
        .getHospSetList(this.current, this.limit, this.searchObj)
        .then(response => {
          this.list = response.data.records;
          this.total = response.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // remove hospital
    removeDataById(id) {
      this.$confirm(
        "the operation will delete the file permenently, do you want to continue?",
        "message",
        {
          confirmButtonText: "confirm",
          cancelButtonText: "cancel",
          type: "warning"
        }
      )
        .then(() => {
          hospset.deleteHospSet(id).then(response => {
            // show message
            this.$message({
              type: "success",
              message: "deleted sucessfully!"
            });
            // refresh page
            this.getList(1)
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "delete cancelled"
          });
        });
    }
  }
};
</script>
