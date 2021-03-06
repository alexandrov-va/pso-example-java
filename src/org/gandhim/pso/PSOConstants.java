package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is an interface to keep the configuration for the PSO
// you can modify the value depends on your needs

public interface PSOConstants {
	int SWARM_SIZE = 30;
	int MAX_ITERATION = 100;
	int PROBLEM_DIMENSION = 2;
	double C1 = 1;
	double C2 = 1;
	double W_UPPERBOUND = 0.9;
	double W_LOWERBOUND = 0.4;
	int MIN_DELAY = 100;
	int MAX_DELAY = 1000;
}
