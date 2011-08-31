package com.oreon.smartsis.web.action.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.math.stat.ranking.NaNStrategy;
import org.apache.commons.math.stat.ranking.NaturalRanking;
import org.apache.commons.math.stat.ranking.TiesStrategy;
import org.jboss.seam.annotations.Name;

import com.oreon.smartsis.domain.ExamScore;
import com.oreon.smartsis.domain.GradeSubject;
import com.oreon.smartsis.domain.Student;

//@Scope(ScopeType.CONVERSATION)
@Name("examInstanceAction")
public class ExamInstanceAction extends ExamInstanceActionBase implements
		java.io.Serializable {

	@Override
	public void prePopulateListExamScores() {
		if (getInstance().getId() == null && listExamScores.isEmpty()) {
			GradeSubject gradeSubject = getInstance().getGradeSubject();
			if (gradeSubject == null)
				return;
			Set<Student> students = getInstance().getGradeSubject().getGrade()
					.getStudents();
			for (Student student : students) {
				ExamScore score = new ExamScore();
				score.setStudent(student);
				score.setExamInstance(this.getInstance());
				listExamScores.add(score);
			}
		}
	}

	@Override
	public String save() {
		if (!listExamScores.isEmpty()) {
			updateAverageAndRank();
		}
		return super.save();
	}

	private void updateAverageAndRank() {
		Integer totalMarks = 0;	
		double[] scores = new double[listExamScores.size()];
		
		for (int i = 0; i < listExamScores.size(); i++) {
			scores[i] = instance.getExam().getMaxMarks() -  listExamScores.get(i).getMarks().doubleValue();
		}
		
		double[] ranks = new NaturalRanking(NaNStrategy.MINIMAL,
				TiesStrategy.MINIMUM).rank(scores);

		for (int i = 0; i < listExamScores.size(); i++) {
			totalMarks += listExamScores.get(i).getMarks();
			listExamScores.get(i).setRank(new Double(ranks[i]).intValue());
		}
		
		int average = totalMarks / listExamScores.size();
		getInstance().setAverage(average);
		
		//Collections.sort(listExamScores, MARKS_DESC);
	}

	static final Comparator<ExamScore> MARKS_DESC = new Comparator<ExamScore>() {
		public int compare(ExamScore es1, ExamScore es2) {
			return es2.getMarks().compareTo(es1.getMarks());
		}
	};
}