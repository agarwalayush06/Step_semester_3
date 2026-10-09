package week8.assigment_problems.code_sprint;

import java.util.*;

class Student {
    String name;

    Student(String name) {
        this.name = name;
    }
}

interface ScoringRule {
    double calculate(double idea, double execution, double presentation);
}

class InnovationTrack implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return idea * 0.5 + execution * 0.3 + presentation * 0.2;
    }
}

class OpenTrack implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Score {
    double idea;
    double execution;
    double presentation;

    Score(double idea, double execution, double presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    double calculate(ScoringRule rule) {
        return rule.calculate(idea, execution, presentation);
    }
}

class Project {
    String name;
    Team team;
    Score score;

    Project(String name, Team team) {
        this.name = name;
        this.team = team;
    }
}

class Team {
    String name;
    List<Student> members;
    ScoringRule track;
    Project project;

    Team(String name, List<Student> members, ScoringRule track) {
        this.name = name;
        this.members = members;
        this.track = track;
    }
}

class Judge {
    public void scoreProject(Project project, double idea,
                             double execution, double presentation,
                             Hackathon hackathon) {
        if (hackathon.isPublished()) {
            System.out.println(
                "Rescore rejected: Results have already been published."
            );
            return;
        }

        if (!hackathon.isJudging()) {
            System.out.println("Scoring rejected: Judging has not started.");
            return;
        }

        if (idea < 0 || idea > 10 ||
            execution < 0 || execution > 10 ||
            presentation < 0 || presentation > 10) {
            System.out.println("Invalid score.");
            return;
        }

        project.score = new Score(idea, execution, presentation);
        System.out.println("Score recorded for '" + project.name + "'.");
    }
}

class Hackathon {
    private String name;
    private String state = "Open";

    private List<Team> teams = new ArrayList<>();
    private Map<String, Team> studentTeams = new HashMap<>();

    Hackathon(String name) {
        this.name = name;
    }

    public boolean registerTeam(Team team) {
        if (!state.equals("Open")) {
            System.out.println("Registration rejected: Registration is closed.");
            return false;
        }

        if (team.members.size() < 2 || team.members.size() > 4) {
            System.out.println(
                "Registration failed: A team must have 2 to 4 members."
            );
            return false;
        }

        for (Student student : team.members) {
            if (studentTeams.containsKey(student.name)) {
                System.out.println(
                    "Registration failed: " + student.name +
                    " already belongs to a team."
                );
                return false;
            }
        }

        teams.add(team);

        for (Student student : team.members) {
            studentTeams.put(student.name, team);
        }

        String trackName = team.track instanceof InnovationTrack
                ? "Innovation" : "Open";

        System.out.println(
            "Team " + team.name + " registered (" +
            team.members.size() + " members, " + trackName + " track)."
        );

        return true;
    }

    public void submitProject(Team team, String projectName) {
        if (!state.equals("Open")) {
            System.out.println("Submission rejected: Submissions are closed.");
            return;
        }

        if (!teams.contains(team)) {
            System.out.println("Submission rejected: Team is not registered.");
            return;
        }

        if (team.project != null) {
            System.out.println("Submission rejected: Team already submitted a project.");
            return;
        }

        team.project = new Project(projectName, team);

        System.out.println(
            "Project '" + projectName + "' submitted by " + team.name + "."
        );
    }

    public void startJudging() {
        if (state.equals("Open")) {
            state = "Judging";
        }
    }

    public void publishResults() {
        if (state.equals("Published")) {
            System.out.println("Results have already been published.");
            return;
        }

        if (!state.equals("Judging")) {
            System.out.println("Cannot publish results before judging.");
            return;
        }

        for (Team team : teams) {
            if (team.project != null && team.project.score != null) {
                double result = team.project.score.calculate(team.track);

                System.out.printf(
                    Locale.US, "Final score: %.2f.%n", result
                );
            }
        }

        state = "Published";
        System.out.println("Results published.");
    }

    public boolean isPublished() {
        return state.equals("Published");
    }

    public boolean isJudging() {
        return state.equals("Judging");
    }
}

public class Main {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        Team byteBusters = new Team(
            "ByteBusters",
            Arrays.asList(asha, ravi, neha),
            new InnovationTrack()
        );

        Team soloCoder = new Team(
            "SoloCoder",
            Arrays.asList(kiran),
            new OpenTrack()
        );

        hackathon.registerTeam(byteBusters);
        hackathon.registerTeam(soloCoder);

        hackathon.submitProject(byteBusters, "SmartAttend");

        hackathon.startJudging();

        Judge judge = new Judge();

        judge.scoreProject(
            byteBusters.project, 8, 7, 9, hackathon
        );

        hackathon.publishResults();

        judge.scoreProject(
            byteBusters.project, 10, 7, 9, hackathon
        );
    }
}