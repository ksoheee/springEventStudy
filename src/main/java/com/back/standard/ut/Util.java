package com.back.standard.ut;

public class Util {
    public static class reflection{
        //obj:필드 값을 변경할 대상 객체, fieldName : 변경할 필드 이름, value:새로 설정할 값
        public static void setField(Object obj, String fieldName, Object value){
            try{
                var field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);  //접근 제한 해제 시도
                field.set(obj, value);      //필드 값 변경
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
