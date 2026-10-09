package week8.class_problems.online_examination;

import java.util.ArrayList;
import java.util.List;

interface Question {
    boolean checkAnswer(String answer);
}

class MCQQuestion implements Question {
    private String question;
    private String correctAnswer;

    public MCQQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public boolean checkAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private List<String> answers = new ArrayList<>();
    private boolean submitted;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void answerQuestion(int number, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers after submission.");
            return;
        }

        while (answers.size() < number) {
            answers.add("");
        }

        answers.set(number - 1, answer);
        System.out.println("Question " + number + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (submitted) {
            System.out.println("Attempt already submitted.");
            return;
        }

        submitted = true;
        int score = 0;

        for (int i = 0; i < examination.getQuestions().size(); i++) {
            if (i < answers.size()
                    && examination.getQuestions().get(i).checkAnswer(answers.get(i))) {
                score++;
            }
        }

        System.out.println("Examination '" + examination.getTitle()
                + "' submitted successfully.");
        System.out.println("Result for '" + examination.getTitle()
                + "' attempt: " + score + "/"
                + examination.getQuestions().size() + " correct");
    }
}

class Examination {
    private String title;
    private List<Question> questions = new ArrayList<>();

    public Examination(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Attempt start(Student student) {
        System.out.println("Examination '" + title
                + "' started by " + student.getName() + ".");
        return new Attempt(student, this);
    }
}

public class Main {
    public static void main(String[] args) {
        Student student = new Student("Ayush");

        Examination exam = new Examination("Math Quiz");
        exam.addQuestion(new MCQQuestion("Question 1", "A"));
        exam.addQuestion(new MCQQuestion("Question 2", "B"));

        Attempt attempt = exam.start(student);
        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");
        attempt.submit();
    }
}