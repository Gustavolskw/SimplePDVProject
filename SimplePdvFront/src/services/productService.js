import axiosClient from "@/Client/AxiosClient";

export const productService = {



    getProducts: async (filtersHead, status) => {
        const filters = { ...filtersHead, status: status != null ? status : null };

        const response = await axiosClient.get("/product", {
            params: filters,
            timeout: 2000,
        });

        return response;
    },
    createProduct: async (data) => {
        return await axiosClient.post(`/product`, data, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
            timeout: 5000,
        });
    },
    updateProduct: async (id, data) => {
        return await axiosClient.put(`/product/${id}`, data, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
            timeout: 5000,
        });
    },
    inactivateProduct: async (id) => {
        return await axiosClient.delete(`/product/${id}`, {
            timeout: 5000,
        });
    },
    reactiveProduct: async (id) => {
        return await axiosClient.put(`/product/${id}/activate`, {
            timeout: 5000,
        });
    },
    includeOnOrder: async (orderId, data) => {
        return await axiosClient.post(
            `/order/${orderId}/include`,
            data,
            {
                timeout: 10000,
            }
        );
    }

}

