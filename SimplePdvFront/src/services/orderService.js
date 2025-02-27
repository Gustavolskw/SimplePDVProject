import axiosClient from "@/Client/AxiosClient";

export const orderService = {
    getOrders: async (filters) => {
        return await axiosClient.get("/order", {
            params: filters,
            timeout: 2000,
        });
    },
    getOrderById: async (id) => {
        return await axiosClient.get(`/order/${id}`, {
            timeout: 2000,
        });
    },
    openOrder: async (data) => {
        return await axiosClient.post("/order", data, {
            timeout: 2000,
        });
    },
    closeOrder: async (id) => {
        return await axiosClient.put(`/order/${id}/close`, null, {
            timeout: 2000,
        });
    },
    cancelOrder: async (id) => {
        return await axiosClient.delete(`/order/${id}/cancel`, null, {
            timeout: 2000,
        });
    }


}