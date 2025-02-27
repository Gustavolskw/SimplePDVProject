import axiosClient from "@/Client/AxiosClient";

export const userService = {
    getUserById: async (id) => {
        return await axiosClient.get(`/user/${id}`, {
            timeout: 5000
        });
    }
}