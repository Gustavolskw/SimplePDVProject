export const saveToken = ({ commit }, { tokenName, tokenValue }) => {
    localStorage.setItem(tokenName, tokenValue);
    commit('SET_TOKEN', { tokenName, tokenValue });
};

export const removeToken = ({ commit }, tokenName) => {
    localStorage.removeItem(tokenName);
    commit('SET_TOKEN', { tokenName, tokenValue: '' });
};
