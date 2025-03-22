package Analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    static int binarySearch(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }


    static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int idx = binarySearch(nums, -(nums[i] + nums[j]));
                if (idx != -1) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[idx]));
                }

            }
        }

        return result;
    }

    boolean search(int[] bitonic, int target) {
        Arrays.sort(bitonic);
        return binarySearch(bitonic, target) != -1;
    }



}