# N-Queens_Problem_Program
N-Queens 문제를 풀기 위한 프로그램 개발 Repository입니다.
<br/><br/>

# 개요
### 문제
8-Queens 문제는 8 × 8 체스보드에서 8개의 여왕이 서로를 공격할 수 없도록 배치하는 문제이다. 체스에서 여왕은 장애물이 없을 경우 전후좌우 그리고 대각선까지 원하는 칸 수만큼 이동할 수 있다. 
즉 체스보드에서 8개의 여왕을 어떻게 배치하면 서로 공격할 수 없는 위치에 놓을 수 있는가에 대한 문제이다. 주어진 체스판의 크기에 대한 여왕 문제의 해를 구한다. 이 때, 해는 여러 개가 존재
할 수 있으므로 모든 해를 구하되 각 크기에 대해 몇 개나 존재하는지 확인한다.
<br/>
### 확장 기능
- GUI 방식으로 구현한다. 
- 해를 탐색하는 과정을 차례로(Step by Step) 보여준다.
- N값이 큰 경우, 모든 해를 구하는데 과도한 시간이 걸릴 수 있다. N 값이 작을 때의 해의 개수 및 실행 시간으로 N값이 큰 경우에 대한 해의 개수 및 실행 시간을 유추해본다.
- 해를 탐색하는 과정에서 일시정지 기능을 제공한다.
<br/><br/><br/>


# 개발
### 목표 설정
- 체스보드에서 N개의 여왕을 어떻게 배치하면 서로 공격할 수 없는 위치에 놓을 수 있는가를 해결하기 위한 프로그램을 제작하는 것이 중점적 목표이며 기능으로서는 하나의 해 뿐만 아니라 모든 해를 구하고 그 과정을 사용자로부터 보여줄 수 있도록 
  만드는 것이 목표이다.


### 개발 환경
- H/W
  - CPU : 11𝑡ℎ Gen Intel® Core™ i5-1135G7 @ 2.40GHz(8cpus) ~ 2.4GHz
  - RAM : 16GB
  - GPU : Internal – Intel® Iris® Xe Graphics, External – NVIDIA GeForce MX450

- S/W
  - OS : WINDOW 10
  - Develop Tool: Eclipse IDE - Java
  - Version : JDK 16
<br/><br/><br/>


# 자료구조
- 모든 해의 정보(좌표)를 담기 위한 구조체 클래스(Queen)
  
|자료형|변수명|내용|
|:---:|:---:|:---:|
|int|row|구한 해의 행을 나타내는 변수|
|int|col|구한 해의 열을 나타내는 변수|
<br/>

- 모든 해를 구하기 위한 클래스(Solve)
  
|자료형|변수명|내용|
|:---:|:---:|:---:|
|int|N|현재 설정되어 있는 체스보드의 크기를 저장하는 변수|
|int|cols|각 행에 들어갈 퀸의 열 좌표를 저장하는 변수|
|ArrayList<Queen>|ans|모든 해의 정보를 저장하기 위한 변수|
|method|getCols()|모든 해의 정보를 저장한 변수(ans) 값을 반환하는 함수|
|method|Possibility()|퀸을 놓을 수 있는 위치인지 확인하여 boolean 값을 반환하는 함수|
|method|backTracking()|백트래킹 알고리즘(문제 해결에 사용되는) 함수|

<br/><br/>
  
# 실행 흐름도
<p align="center">
  <img src="https://user-images.githubusercontent.com/74342121/148163070-93d282bc-380c-4a2b-a2de-bef2b8fb1f6e.png" width="1200" height="600">
</p>
  
<br/><br/>
  
# 시스템 구성
<p align="center">
  <img src="https://user-images.githubusercontent.com/74342121/148163217-22aaaff2-1043-463e-9376-d106d4670abe.png" width="800" height="300">
</p>  
