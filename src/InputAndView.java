import java.util.Scanner;

public class InputAndView {
    private static final Storage storage = new Storage();
    private static final Scanner scanner = new Scanner(System.in);
    private static String option_select = "";


    public void showMain() {
        showIntro();
        while(!option_select.equals("5")){
            showOptions();
        }
    }

    private void showIntro() {
        System.out.println("============== START ===============");
    }

    private void showOptions(){
        System.out.println("--------- SELECT OPTIONS -----------");
        System.out.println("옵션을 선택하세요: 0.예제추가, 1.문제추가, 2.문제삭제, 3.문제조회, 4.문제풀기 5.프로그램 종료");
        System.out.print("숫자(1~5) 입력 >> ");
        option_select = scanner.nextLine();
        switch (option_select) {
            case "0":
                addExampleProblems();
                break;
            case "1":
                addProblem();
                break;
            case "2":
                deleteProblem();
                break;
            case "3":
                displayAllProblems();
                break;
            case "4":
                solveProblem();
                break;
            case "5":
                exitProgram();
                break;
            default:
                System.out.println("----------------------------------");
                System.out.println("잘못된 입력입니다.");
                break;
        }
    }

    private void addExampleProblems() {
        storage.addProblem("1. Python에서 변수를 선언하는 방법은? (점수: 10점)\n" +
                "1) var name 2) name = value 3) set name 4) name == value", "4");
        storage.addProblem("2. Python에서 리스트(List)의 특징은 무엇인가요? (점수: 15점)\n" +
                "1) 순서가 있고 변경 가능하다, 2) 중복된 값을 가질 수 없다, 3) 원소를 추가하거나 삭제할 수 없다, 4) 정렬된 \n" +
                "상태로 유지된다", "2");
        storage.addProblem("3. Python에서 조건문을 작성하는 방법은? (점수: 10점)\n" +
                "1) if-else, 2) for-in, 3) while, 4) def", "3");
        storage.addProblem("4. Python에서 함수를 정의하는 방법은? (점수: 5점)\n" +
                "1) class, 2) def, 3) import, 4) return", "2");
    }


    private void addProblem() {
        System.out.println("----------------------------------");
        System.out.print("문제를 추가하시겠습니까? (Y/N) >> ");
        String YN_select = scanner.nextLine();
        if(YN_select.equals("y") || YN_select.equals("Y") || YN_select.equals("ㅛ")){
            System.out.print("질문을 입력해주세요 >> ");
            String question = scanner.nextLine();
            System.out.print("선택지를 입력해주세요 >> ");
            String selection = scanner.nextLine();
            System.out.print("답을 입력해주세요 >> ");
            String answer = scanner.nextLine();

            String completedQuestion = question + "\n" + selection;
            int id = storage.addProblem(completedQuestion, answer);
            System.out.println("저장된 ID값: "+ id);
        }else if (YN_select.equals("n") || YN_select.equals("N")) {
            showOptions();
        }else{
            System.out.println("잘못된 입력입니다. Y 또는 N 으로 답변해주세요.");
            addProblem();
        }
    }

    private void deleteProblem() {
        System.out.println("----------------------------------");
        if(storage.getProblemCount()==0) {
            System.out.println("삭제할 문제가 없습니다.");
            showOptions();
        }else {
            System.out.print("문제를 삭제하시겠습니까? (Y/N) >> ");
            String YN_select = scanner.nextLine();
            if(YN_select.equals("y") || YN_select.equals("Y") || YN_select.equals("ㅛ")){
                System.out.print("삭제할 문제의 ID값을 입력해주세요 (취소: 0) >> ");
                String workID = scanner.nextLine();
                try {
                    Integer ID = Integer.parseInt(workID);
                    if(ID ==0) {
                        showOptions();
                    }
                    else {
                        int result = storage.removeWork(ID);
                        if(result==0) {
                            System.out.println("삭제 실패");
                        }else {
                            System.out.println("ID: "+ ID +" - 삭제 완료");
                        }
                    }
                }catch(Exception e) {
                    System.out.println("잘못된 입력입니다.");
                    deleteProblem();
                }
            }else if (YN_select.equals("n") || YN_select.equals("N")) {
                showOptions();
            }else{
                System.out.println("잘못된 입력입니다. Y 또는 N 으로 답변해주세요.");
                deleteProblem();
            }
        }
    }

    private void displayAllProblems() {
        System.out.println("----------------------------------");
        if(storage.getProblemMap().keySet().isEmpty()) {
            System.out.println("문제 목록이 비어있습니다.");
        }else {
            System.out.print("문제 목록을 조회하시겠습니까? (Y/N) >> ");
            String YN_select = scanner.nextLine();
            if(YN_select.equals("y") || YN_select.equals("Y") || YN_select.equals("ㅛ")){
                for( Integer ID : storage.getProblemMap().keySet()){
                    System.out.println("ID=="+ID+", problem=="+ storage.getProblemMap().get(ID).getQuestion() );
                    System.out.println("답=="+ storage.getProblemMap().get(ID).getAnswer() );
                    System.out.println();
                }
            }else if (YN_select.equals("n") || YN_select.equals("N")) {
                showOptions();
            }else{
                System.out.println("잘못된 입력입니다. Y 또는 N 으로 답변해주세요.");
                displayAllProblems();
            }
        }
    }

    private void solveProblem() {
        if(storage.getProblemMap().keySet().isEmpty()) {
            System.out.println("문제 목록이 비어있습니다.");
        }else {
            int randomKey = storage.getRandomProblemId();
            System.out.println(storage.getProblemQuestion(randomKey));
            System.out.print("정답을 입력해주세요 >> ");
            String userAnswer = scanner.nextLine();
            boolean result = storage.checkAnswer(userAnswer);
            if(result){
                System.out.println("정답입니다.");
            }else {
                System.out.print("틀렸습니다.  ");
                System.out.println("정답: " + storage.getProblemAnswer(randomKey));
            }
        }
    }

    private void exitProgram() {
        System.out.println("----------------------------------");
        System.out.println("프로그램을 종료합니다.");
        System.out.println("============= FINISH ===============");
    }


}
