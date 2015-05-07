package com.jike.spring.chapter03.ioc;


public class WuJianDao implements ActorArrangable {
	private LiuJianming ljm;

	public void injectLjm(LiuJianming ljm) {
		this.ljm = ljm;		
	}
	
	public void declare() {
		ljm.declare("我只是想做一个好人！");
	}

    /*
     *
     * @param args
     */
    public static void main(String[] args) {

        Director dr = new Director();
        dr.direct();

    }
}