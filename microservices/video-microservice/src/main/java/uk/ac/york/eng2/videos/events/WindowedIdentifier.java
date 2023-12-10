package uk.ac.york.eng2.videos.events;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class WindowedIdentifier {

	private Long id, startMillis, endMillis;

	public WindowedIdentifier() {
		// empty constructor for reflection
	}

	public WindowedIdentifier(Long id, Long startMillis, Long endMillis) {
		this.id = id;
		this.startMillis = startMillis;
		this.endMillis = endMillis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WindowedIdentifier [id=" + id + ", startMillis=" + startMillis + ", endMillis=" + endMillis + "]";
	}

}
