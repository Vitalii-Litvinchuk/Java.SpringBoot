import { string, object } from "yup";

export const LoginSchema = object({
    email: string()
        .email("Не коректно вказана пошта")
        .required("Вкажіть пошту"),

    password: string()
        .required("Вкажіть пароль"),
});