package com.power.plus.activity;

import static org.fest.assertions.api.ANDROID.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OutageActvityTest {
	private OutageActivity outageActivity;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldNotBeNull() {

		outageActivity = Robolectric.buildActivity(OutageActivity.class)
				.create().start().resume().get();
		assertThat(outageActivity).isNotNull();
	}

}
