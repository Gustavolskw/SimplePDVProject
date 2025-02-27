export const SET_TOKEN = (state, { tokenName, tokenValue }) => {
    state.tokens[tokenName] = tokenValue;
};

