package grondag.canvas.chunk.occlusion;

import java.util.Arrays;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class AreaFinder {
	private static final Area[] AREA;

	public static final int AREA_COUNT;

	public Area get(int index) {
		return AREA[index];
	}

	static {
		final IntOpenHashSet keys = new IntOpenHashSet();

		keys.add(AreaUtil.areaKey(0, 0, 15, 15));

		keys.add(AreaUtil.areaKey(1, 0, 15, 15));
		keys.add(AreaUtil.areaKey(0, 0, 14, 15));
		keys.add(AreaUtil.areaKey(0, 1, 15, 15));
		keys.add(AreaUtil.areaKey(0, 0, 15, 14));

		for (int x0 = 0; x0 <= 15; x0++) {
			for (int x1 = x0; x1 <= 15; x1++) {
				for (int y0 = 0; y0 <= 15; y0++) {
					for(int y1 = y0; y1 <= 15; y1++) {
						keys.add(AreaUtil.areaKey(x0, y0, x1, y1));
					}
				}
			}
		}

		AREA_COUNT = keys.size();

		AREA = new Area[AREA_COUNT];

		int i = 0;

		for(final int k : keys) {
			AREA[i++] = new Area(k, 0);
		}

		Arrays.sort(AREA, (a, b) -> {
			final int result = Integer.compare(b.areaSize, a.areaSize);

			// within same area size, prefer more compact rectangles
			return result == 0 ? Integer.compare(a.edgeCount, b.edgeCount) : result;
		});

		// PERF: minor, but sort keys instead array to avoid extra alloc at startup
		for (int j = 0; j < AREA_COUNT; j++) {
			AREA[j] = new Area(AREA[j].areaKey, j);
		}
	}

	final long[] bits = new long[4];

	public final ObjectArrayList<Area> areas =  new ObjectArrayList<>();

	public void find(long[] bitsIn, int sourceIndex) {
		areas.clear();
		final long[] bits = this.bits;
		System.arraycopy(bitsIn, sourceIndex, bits, 0, 4);

		final long hash = AreaUtil.areaHash(bits);

		for(final Area r : AREA) {
			if (r.matchesHash(hash) && r.isIncludedBySample(bits, 0)) {
				areas.add(r);
				r.clearBits(bits, 0);

				if (hash == 0) {
					break;
				}
			}
		}
	}

	// PERF: start later in search for smaller samples
	// or search by hash
	public Area findLargest(long[] bitsIn, int sourceIndex) {
		for(final Area r : AREA) {
			if (r.isIncludedBySample(bitsIn, sourceIndex)) {
				return r;
			}
		}

		return  null;
	}
}