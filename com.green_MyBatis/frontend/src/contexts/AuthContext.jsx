import { createContext, useState, useEffect } from "react";

// 저장소 생성
export const AuthContext = createContext();

export default function AuthProvider({children}){

    // [상태 정의] 로그인한 사용자의 정보를 담는 변수
    // 초기값은 null(로그아웃 상태)
    const[user,setUser] = useState(null);

    // 자동 로그인 처리 (useEffect 이용, 딱 한번 실행) -> 브라우저가 새로고침되더라도 로그인이 유지되도록
    // 컴포넌트가 처음 화면에 나타날때(Mount) 딱 한번 실행
    useEffect(()=>{
        // 서버에서 세션에 정보를 저장했기때문에 react에서 정보를 확인할 수 없음 그래서 여기서도 세션에서 관리하여 눈으로 확인할 수 있도록 해줌
        // 세션 스토리지에서 'user'라는 이름으로 저장된 문자열을 가져옴
        // ※ 세션 스토리지는 웹에서 탭을 종료하면 무조건 삭제됨(localStorage는 영구 삭제 안됨)
        const saveUser = sessionStorage.getItem('user');

        // 저장된 데이터가 있다면 문자열을 다시 자바스크립트 객체로 변환(JSON.parse)하여 상태에 저장해서 화면에서 사용
        if(saveUser){
            setUser(JSON.parse(saveUser));
        }

    },[]);

    // - 로그인 함수
    // 로그인 성공 시 서버로부터 받은 사용자 데이터(userData)를 매개변수로 받음
    const login = (userData)=>{
        // 세션 스토리지는 문자열만 저장 가능 => 객체를 문자열로 변환 : JSON.stringify()사용
        sessionStorage.setItem('user',JSON.stringify(userData));

        // React는 상태를 업데이트할 때 랜더링됨
        setUser(userData);
    }

    // - 로그아웃 함수
    const logout = () =>{
        // 세션 스토리지에 존재하는 정보 삭제
        sessionStorage.removeItem('user');

        // 로그아웃이 되려면 다시 null상태로 업데이트 해야함
        setUser(null);
    }

    // 데이터 공급 : Provider 사용
    return(
        <AuthContext.Provider value={{user, login, logout}}>
            {/* ★ Provider 사이에 위치한 모든 컴포넌트들은 위 value를 공유받음 */}
            {children}
        </AuthContext.Provider>
    )
}