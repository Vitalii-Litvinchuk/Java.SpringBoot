import { Form, FormikProvider, useFormik } from "formik";
import { useRef, useState } from "react";
import ReCAPTCHA from "react-google-recaptcha";
import { Link } from "react-router-dom";
import { useActions } from "../../../hooks/useActions";
import { captchaKey } from "../../../recaptcha";
import InputGroup from "../../common/InputGroup";
import { IRegisterModel } from "./types";
import { RegisterSchema } from "./validation";


const RegisterPage = () => {
    const initialState: IRegisterModel = {
        name: "",
        email: "",
        password: "",
        password_confirmation: "",
        token: ""
    }

    const [error, setError] = useState<string>("");

    const reRef = useRef<ReCAPTCHA>(null);

    const { UserRegister } = useActions();

    const onHandleSubmit = async (
        values: IRegisterModel,
        // { setFieldError }: FormikHelpers<IRegisterModel>
    ) => {
        try {
            if (values.token) {
                setError("");
                UserRegister(values, setError);
            } else setError("Підтвердіть, що ви не робот");
        } catch (ex) {
            // console.log(ex);
        }
    }


    const formik = useFormik({
        initialValues: initialState,
        validationSchema: RegisterSchema,
        onSubmit: onHandleSubmit
    });

    const { errors, touched, handleChange, handleSubmit, setFieldValue } = formik;

    const onChangeCaptcha = (token: string | null) => {
        if (token !== null) {
            setError("");
            setFieldValue("token", token);
        }
    }

    return (
        <>
            <h1 className="text-center mt-3">Реєстрація</h1>
            <div className="container">
                <div className="row justify-content-center align-items-center">
                    <div className="col-md-10 col-lg-8 col-xl-5 bg-light shadow-lg p-3 bg-white rounded p-4">
                        {error !== "" ?
                            <div className="text-center alert-danger m-1 p-2 rounded shadow-lg">
                                {error}{reRef.current?.reset()}
                            </div> : <></>}
                        <FormikProvider value={formik}>
                            <Form onSubmit={handleSubmit}>
                                <div className="form-group">
                                    <InputGroup label="Ім'я"
                                        field="name"
                                        error={errors.name}
                                        touched={touched.name}
                                        onChange={handleChange} />
                                </div>
                                <div className="form-group mt-3">
                                    <InputGroup
                                        label="Електронна пошта"
                                        field="email"
                                        error={errors.email}
                                        touched={touched.email}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="form-group mt-3">
                                    <InputGroup label="Пароль"
                                        field="password"
                                        type="password"
                                        error={errors.password}
                                        touched={touched.password}
                                        onChange={handleChange} />
                                </div>
                                <div className="form-group mt-3">
                                    <InputGroup label="Підтвердити пароль"
                                        field="password_confirmation"
                                        type="password"
                                        error={errors.password_confirmation}
                                        touched={touched.password_confirmation}
                                        onChange={handleChange} />
                                </div>
                                <div>
                                    <ReCAPTCHA
                                        sitekey={captchaKey}
                                        size="normal"
                                        ref={reRef}
                                        onChange={onChangeCaptcha}
                                    />
                                </div>
                                <div className="my-2 text-center">
                                    <button type="submit" className="btn btn-outline-primary btn-lg m-auto px-5"
                                    >Реєстрація</button>
                                </div>
                                <div className="my-1 text-center ">
                                    <Link to="/login" className="btn btn btn-outline-success px-3">Вже маю акаунт</Link>
                                </div>
                            </Form>
                        </FormikProvider>
                    </div>
                </div>
            </div>
        </>)
}

export default RegisterPage;