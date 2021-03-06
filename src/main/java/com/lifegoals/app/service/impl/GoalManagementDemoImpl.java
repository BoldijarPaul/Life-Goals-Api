package com.lifegoals.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lifegoals.app.entities.Goal;
import com.lifegoals.app.entities.SavedGoal;
import com.lifegoals.app.service.ServiceLocator;
import com.lifegoals.app.service.helper.CacheArrayList;
import com.lifegoals.app.service.interf.IGoalManagement;

public class GoalManagementDemoImpl implements IGoalManagement {
	private static List<Goal> goals = null;

	public GoalManagementDemoImpl() {
		if (goals == null) {
			goals = new CacheArrayList<Goal>("cache_goals");

		}
	}

	@Override
	public List<Goal> getAllGoals() {
		List<Goal> userGoals = new ArrayList<Goal>();
		for (Goal goal : goals) {
			/* only return public goals */
			if (goal.isPublicGoal() && goal.isVisible()) {
				userGoals.add(goal);
			}
		}
		return userGoals;
	}

	@Override
	public List<Goal> getUserGoals(int userId, boolean showPrivateGoals) {
		List<Goal> userGoals = new ArrayList<Goal>();
		for (Goal goal : goals) {
			if (goal.getUserId() == userId && goal.isPublicGoal()
					&& goal.isVisible()) {
				userGoals.add(goal);
			} else {
				if (showPrivateGoals && goal.getUserId() == userId
						&& goal.isVisible()) {
					/* the goal is private and we want to get it as well */
					userGoals.add(goal);
				}
			}

		}
		return userGoals;
	}

	@Override
	public Goal deleteGoal(int id) {
		/* a goal can't be deleted from the server, it will just be hidden */
		Goal goal = null;
		for (int i = 0; i < goals.size(); i++) {
			if (goals.get(i).getId() == id) {
				goal = goals.get(i);
				goal.setVisible(false);
			}
		}

		return goal;
	}

	@Override
	public Goal addGoal(Goal goal) {
		goal.setId((int) (Math.random() * 1000));
		goals.add(goal);

		/* goal added, now create a saved goal for this guy */
		SavedGoal savedGoal = new SavedGoal();
		savedGoal.setDone(false);
		savedGoal.setGoalId(goal.getId());
		savedGoal.setUserId(goal.getUserId());

		ServiceLocator.get().getSavedGoalManagement().addSavedGoal(savedGoal);

		return goal;
	}

	@Override
	public Goal getGoalById(int id) {
		for (Goal goal : goals) {
			if (goal.getId() == id) {
				return goal;
			}
		}
		return null;
	}

}
