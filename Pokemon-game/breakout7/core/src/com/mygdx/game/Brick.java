package com.mygdx.game;

/* Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */

public class Brick {
	float xmin, ymin, xmax, ymax, angle, res;
	String mode;
	
	public Brick(float xmin, float ymin, float xmax, float ymax, float angle, float res, String mode){
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
		this.angle = angle;
		this.res = res;
		this.mode = mode;
	}

}
