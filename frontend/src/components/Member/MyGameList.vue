<template>
  <div id="view">
    <!-- <h1>내가 만든 게임 목록 입니다.</h1> -->
    <div id="list">
      <my-game-item
        v-for="(game, index) in myGames"
        :key="index"
        :game="game"
        :index="index"
        @updateProps="(value) => updateMyGames(value)"
      ></my-game-item>
    </div>
    <loading-spinner id="imgLoading" v-if="isLoading">
      <!-- 이미지 로딩 중 -->
    </loading-spinner>
    <!-- <div id="noItem" v-if="(!myGames || !myGames.length) && !isLoading">
      게임을 등록 해주세요!
    </div> -->
    <page-nav
      :totalPageCount="totalPages"
      :value="pageConditionReq.page"
      @change="(num) => changePage(num)"
    ></page-nav>
  </div>
</template>

<script setup>
// import NormalGame from "@/components/GameList/item/NormalGame";
import MyGameItem from "@/components/Member/item/MyGameItem.vue";
import { apiInstance } from "@/api/index";
import { useStore } from "vuex";
import { ref } from "vue";
import LoadingSpinner from "./item/LoadingSpinner.vue";
import PageNav from "@/components/common/PageNav.vue";

const store = useStore();
const api = apiInstance();
const isLoading = ref(true);
const myGames = ref();
let totalPages = ref();
//페이징 이동
const changePage = (page) => {
  pageConditionReq.value.page = page;
  callApi();
};
let pageConditionReq = ref({
  order: "RECENT",
  period: "ALL",
  search: "GAME",
  keyword: "",
  page: 1,
});

const callApi = () => {
  api
    .get(`/api/members/games`, {
      params: {
        order: pageConditionReq.value.order,
        period: pageConditionReq.value.period,
        search: pageConditionReq.value.search,
        keyword: pageConditionReq.value.keyword,
        page: pageConditionReq.value.page,
      },
    })
    .then((response) => {
      myGames.value = response.data.content;
      totalPages = response.data.totalPages;
    })
    .catch((error) => {
      console.log(error);
      // if (error.response.status == 401) {
      //   getAccessTokenByRefreshToken(); // refresh 토큰으로 다시
      // }
    })
    .finally(() => {
      isLoading.value = false;
    });
};

const updateMyGames = (value) => {
  myGames.value.splice(value, 1);
};

callApi();

store.dispatch("commonStore/setMemberTabAsync", 2);
</script>

<style scoped>
#view {
  width: 100%;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

#list {
  display: grid;
  gap: 35px 0;
  grid-template-columns: repeat(3, 1fr);
  width: 1090px;
  margin: 2%;
}

#noItem {
  font-size: 20px;
}
</style>
