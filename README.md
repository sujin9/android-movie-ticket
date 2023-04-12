# android-movie-ticket

# 기능 목록
- [x] 레이아웃을 만든다.
  - 메인, 상세 정보, 예매 완료 페이지
  - 리스트 아이템
- [x] 메인 페이지에 영화 리스트가 보인다.
- [x] 영화를 선택하면 (리스트 아이템, 버튼) 상세 정보 페이지로 이동한다.
- [x] 상세 정보 페이지에서 예약할 인원을 선택한다.
- [x] 상세 정보 페이지에서 예매 완료를 선택하면 예매 완료 페이지로 이동한다.
- [x] 예매 완료 페이지에서 인원과 가격을 보여준다.

- [x] 상영 날짜 `범위`를 갖는다.
- [x] 영화를 볼 날짜를 선택한다. (`스피너` 이용)
- [ ] 영화를 볼 시간을 선택한다. (`스피너` 이용)
  - [ ] 평일 시간의 범위는 오전 9시부터 자정까지 2시간 간격이다.
  - [ ] 주말 시간의 범위는 오전 10시부터 자정까지 2시간 간격이다.
- [ ] 할인 조건에 따라 다른 할인 정책이 적용된다.
  - [ ] 무비데이: 10, 20, 30일 -> 10% 할인
  - [ ] 조조/야간: 11시 이전, 20시 이후 -> 2000원 할인
  - 무비데이 할인이 선적용, 중복 할인 가능
- [ ] 화면이 가로로 전환되어도 데이터가 유지된다.