const TOKEN_KEY = 'auth_token';

export default {
    namespaced: true,
    state: {
        token: localStorage.getItem(TOKEN_KEY) || null,
    },
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token;
            localStorage.setItem(TOKEN_KEY, token);
        },
        CLEAR_TOKEN(state) {
            state.token = null;
            localStorage.removeItem(TOKEN_KEY);
        },
    },
    actions: {
        saveToken({ commit }, token) {
            commit('SET_TOKEN', token);
        },
        removeToken({ commit }) {
            commit('CLEAR_TOKEN');
        },
    },
    getters: {
        isAuthenticated: (state) => !!state.token,
        getToken: (state) => state.token,
    },
};
