@@ -0,0 +1,48 @@
# My ToDo List(일정관리 앱)

# 소개

My ToDo List(일정관리 앱)은 할 일을 저장하거나, 습관을 기록할 수 있는 어플 입니다.  또한 캘린더로 저장한 일정을 보여줍니다.

기간: 2020/12/24 ~ 2021/01/21

- 할 일을 저장할 수 있습니다.
- 자신의 습관을 기록 할 수 있습니다.
- 시간을 설정하면 알림이 가능합니다.

## 구현한 기능

- Firebase Authentication을 이용해 로그인, 회원가입 가능
- 날짜, 시간을 이용해 AlarmManager, NotificationManager, BroadcastReceiver를 이용하여 푸시 알림 구현
- FireStore의 데이터가 변경 될 때마다 새 값으로 다시 실행되는 addSnapshotListener를 이용하여 FireStore에 실시간으로 추가, 삭제, 읽기 가능
- 할 일을 완료하여 체크박스 클릭시 완료 Collection으로 이동
- 왼쪽으로 스와이프하여 할 일, 습관 삭제 가능
- 오픈소스 캘린더를 이용하여 캘린더에 할 일이 있는 요일을 표시
- MVVM (Model + View + ViewModel) 구조 사용 (조금 미흡)

## 스크린샷

------

<img src = "https://user-images.githubusercontent.com/61860897/105350833-12967280-5c2f-11eb-95f3-5527a355f1d9.jpg" width="300px"><img src = "https://user-images.githubusercontent.com/61860897/105350836-132f0900-5c2f-11eb-9d12-117ebff5ecff.jpg" width="300px"><img src = "https://user-images.githubusercontent.com/61860897/105350842-14603600-5c2f-11eb-9663-35c1399cb320.jpg" width="300px"><img src = "https://user-images.githubusercontent.com/61860897/105350847-15916300-5c2f-11eb-917c-a16a47c9e432.jpg" width="300px"><img src = "https://user-images.githubusercontent.com/61860897/105350890-2346e880-5c2f-11eb-9204-f6e23632eb49.jpg" width="300px"><img src = "https://user-images.githubusercontent.com/61860897/105350897-25a94280-5c2f-11eb-9310-99246483bb76.jpg" width="300px">



<img src = "https://user-images.githubusercontent.com/61860897/105350900-26da6f80-5c2f-11eb-87da-e460e70b14c8.jpg" width="300px"><img src = "https://user-images.githubusercontent.com/61860897/105350904-280b9c80-5c2f-11eb-9aa4-9167cb477e2c.jpg" width="300px">





## 사용 기술

- MVVM (Model + View + ViewModel)
- DataBinding
- Java
- Firebase Authentication
- FireStore
- Swipe
- AlarmManager
- NotificationManager
- BroadcastReceiver
- [Material-Calendar-View(오픈소스사용)](https://github.com/Applandeo/Material-Calendar-View)
