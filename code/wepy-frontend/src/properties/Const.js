var httpHead = "http://202.120.40.109:23385";
var imgHead = "http://p9hefrt2w.bkt.clouddn.com/";
const winW = wx.getSystemInfoSync().screenWidth; // 屏幕宽度
const ratio = 750 / winW //px && rpx 单位转换 (乘于 这个属性是 px 转换成 rpx)


export  {httpHead, imgHead, winW, ratio}
