import axiosClient from "@/Client/AxiosClient";



export const prodcutTypeServcie = {
    getProductTypes: async (filters) => {
        return axiosClient.get("/product/type", {
            params: filters ? filters : null,
            timeout: 2000,
        });
    },
    deleteProductTypes: async (id) => {
        return await axiosClient.delete(`/product/type/${id}`, {
            timeout: 5000,
        });
    },
    createProductType: async (id, name) => {

        return await axiosClient.post(
            `/product/type`,
            {
                name: name,
            },
            {
                timeout: 5000,
            }
        );
    }
}