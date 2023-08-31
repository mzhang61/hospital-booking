import request from '@/utils/request'

export default {
  // data dictionary list
  dictList(id) {
    return request({
      url: `/admin/cmn/dict/findChildData/${id}`,
      method: 'get'

    })
  }
}
