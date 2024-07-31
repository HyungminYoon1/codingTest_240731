import java.util.*;

public class Storage {
    private static int ID;
    public static final Map<Integer, Problem> problemMap = new HashMap<>();

    public Map<Integer, Problem> getProblemMap() {
        return problemMap;
    }


    public static <K, V> K getRandomKeyFromMap(Map<K, V> map) {
        // Map의 키를 List로 변환
        List<K> keys = new ArrayList<>(map.keySet());
        // Random 객체 생성
        Random random = new Random();
        // List에서 랜덤 인덱스 선택
        int randomIndex = random.nextInt(keys.size());
        // 랜덤 키 반환
        return keys.get(randomIndex);
    }

    protected int addProblem(String question, String answer) {
        Problem problem = new Problem(question, answer);
        ID++;
        problemMap.put(ID, problem);
        return ID;
    }

    protected int removeWork(Integer ID) {
        int result;
        if(problemMap.get(ID)==null){
            result = 0;
        }else {
            problemMap.remove(ID);
            result = 1;
        }
        return result;
    }


    protected String getProblemQuestion(int id) {
        return problemMap.get(ID).getQuestion();
    }
    protected String getProblemAnswer(int id) {
        return problemMap.get(ID).getAnswer();
    }

    protected boolean checkAnswer(String answer) {
        return answer.equals(problemMap.get(ID).getAnswer());

    }

    protected int getProblemCount() {
        int result = 0;
        result = problemMap.keySet().size();
        return result;
    }

    protected int getRandomProblemId() {
        if(getProblemCount() == 0) {
            return 0;
        }else {
            return Integer.parseInt(String.valueOf(getRandomKeyFromMap(problemMap))) ;
        }
    }
}
