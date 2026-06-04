import {useState} from "react";

export const useApi = <T>() => {

    const [loading, setLoading] =
        useState(false);

    const [error, setError] =
        useState<string | null>(null);

    const execute = async (
        url: string,
        options?: RequestInit
    ): Promise<T> => {

        try {
            setLoading(true);
            setError(null);

            const response =
                await fetch(url, options);

            if (!response.ok) {
                throw new Error(response.statusText);
            }

            return await response.json();
        } catch (err) {
            const message =
                err instanceof Error
                    ? err.message
                    : "Unknown error";

            setError(message);
            throw err;
        } finally {
            setLoading(false);
        }
    };

    return {
        execute,
        loading,
        error
    };
}