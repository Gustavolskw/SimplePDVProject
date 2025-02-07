export const getToken = (state) => (tokenName) => state.tokens[tokenName] || '';
export const isLogged = (state) => !!state.tokens.authToken; // Example for authToken
