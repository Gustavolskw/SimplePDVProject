export default {
    tokens: {
        authToken: localStorage.getItem('authToken') || '',
        orderToken: localStorage.getItem('orderToken') || '',
    },
};
