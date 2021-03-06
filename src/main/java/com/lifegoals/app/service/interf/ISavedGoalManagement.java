package com.lifegoals.app.service.interf;

import java.util.List;

import com.lifegoals.app.entities.SavedGoal;

public interface ISavedGoalManagement {
	/*
	 * gets all the user goals , if the expandGoals is true, the Goal variable
	 * will also be loaded
	 */
	public List<SavedGoal> getUserSavedGoals(int userId);

	public SavedGoal addSavedGoal(SavedGoal savedGoal);

	public SavedGoal deleteSavedGoal(int id);

	public void loadSavedGoalsGoals(List<SavedGoal> goals);

	/* will update the saved goal, by it's ID */
	public SavedGoal updateSavedGoal(SavedGoal savedGoal);

	/* returns true if the user already has a saved goal with that goal id */
	public boolean savedGoalAlreadyExists(int userId, int goalId);
}
