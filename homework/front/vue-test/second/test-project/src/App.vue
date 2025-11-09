<template>
  <div id="app">
    <!-- <user-view></user-view> -->
     <!-- <question-view></question-view> -->
      <router-view></router-view>
  </div>
</template>

<script>
// import UserView from './views/element/UserView.vue';
// import QuestionView from './views/element/QuestionView.vue';

export default {
  // components: { UserView },
//   components: { QuestionView },
  data() {
    return {
      message: "Hello, Vue",
    };
  },
  methods: {
    
  },
};
</script>


<style>
</style>
