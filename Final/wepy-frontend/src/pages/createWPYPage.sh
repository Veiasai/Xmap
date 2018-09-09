fileName="$1"
echo "Create new component view: $fileName"
touch "$fileName.wpy"
cat>$fileName.wpy<<EOF
<style lang="less">

</style>


<template>
<view></view>
</template>

<script>
  import wepy from 'wepy';

  export default class $fileName extends wepy.page {

    components = {
    }

    data = {
      

    };

    methods = {


    };

    onLoad() {

    }

    config={
      navigationBarTitleText: '$fileName'
    }
  }
</script>
EOF

echo "Create page $fileName finished"