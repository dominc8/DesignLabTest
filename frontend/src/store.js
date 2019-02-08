import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    storedUsername: "",
    storedPassword: ""
  },
  mutations: {
    setUsername: (state, username) => {
      state.storedUsername = username;
    },
    setPassword: (state, password) => {
      state.storedPassword = password;
    },
  },
  actions: {
    vuexSetUserAction: (context, username, password) => {
      context.commit("vuexSetUser", username, password)
    }
  }
});
