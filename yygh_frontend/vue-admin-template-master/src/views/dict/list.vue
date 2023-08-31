<template>
  <div class="app-container">
    <div class="el-toolbar">
      <div class="el-toolbar-body" style="justify-content: flex-start;">
        <a href="http://localhost:8202/admin/cmn/dict/exportData" target="_blank">
          <el-button type="text"><i class="fa fa-plus" /> export</el-button>
        </a>
        <el-button type="text" @click="importData"><i class="fa fa-plus" /> import</el-button>
      </div>
    </div>
    <el-table :data="list" style="width: 100%" row-key="id" border lazy :load="getChildren"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <el-table-column label="name" width="230" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <el-table-column label="code" width="220">
        <template slot-scope="{ row }">
          {{ row.dictCode }}
        </template>
      </el-table-column>
      <el-table-column label="value" width="230" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.value }}</span>
        </template>
      </el-table-column>
      <el-table-column label="created time" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="import" :visible.sync="dialogImportVisible" width="480px">
      <el-form label-position="right" label-width="170px">

        <el-form-item label="file">
          <el-upload :multiple="false" :on-success="onUploadSuccess"
            :action="'http://localhost:8202/admin/cmn/dict/importData'" class="upload-demo">
            <el-button size="small" type="primary">upload</el-button>
            <div slot="tip" class="el-upload__tip">only xls file，and it does not exceeding 500kb</div>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogImportVisible = false">
          cancel
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import dict from "@/api/dict";
export default {
  data() {
    return {
      dialogImportVisible: false, // false does not pop out the window
      list: []
    }
  },
  created() {
    this.getDictList(1);
  },
  methods: {
    // import data
    importData() {
      this.dialogImportVisible = true
    },
    onUploadSuccess() {
      // cancel message window
      this.dialogImportVisible = false
      // refresh the page
      this.getDictList(1)
    },
    // export data
    exportData() {
      window.location.href = "http://localhost:8202/admin/cmn/dict/exportData"
    },

    getDictList(id) {
      dict.dictList(id).then(response => {
        this.list = response.data
      });
    },
    getChildren(tree, treeNode, resolve) {
      dict.dictList(tree.id).then(response => {
        resolve(response.data)
      });
    }
  }
};
</script>
