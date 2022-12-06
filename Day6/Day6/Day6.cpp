#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <set>

using namespace std;

bool contains_duplicates(queue<char> characters) {
    set<char> uniques;
    queue<char> queue_copy(characters);
    while (!queue_copy.empty()) {
        uniques.insert(queue_copy.front());
        queue_copy.pop();
    }
    return uniques.size() != characters.size();
}

int task(string input, int queue_size) {
    vector<char> v(input.begin(), input.end());
    queue<char> last_four;
    for (int i = 0; i < v.size(); ++i) {
        if (last_four.size() > queue_size) {
            last_four.pop();
            if (!contains_duplicates(last_four)) return i;
        }
        last_four.push(v[i]);
    }
    return 0;
}


int task2(string input) {
    return task(input, 14);
}

int task1(string input) {
    return task(input, 4);
}

int main()
{
    string input;
    cin >> input;
    cout << "Task 1: " << task1(input) << endl;
    cout << "Task 2: " << task2(input) << endl;
}
