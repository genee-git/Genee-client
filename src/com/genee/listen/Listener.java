package com.genee.listen;

public interface Listener {

	public void start() throws Exception;

	public void stop();

	public void onListen(Response response);

	public interface Response {
		public void get(SpeechData speechData);
	}

}
