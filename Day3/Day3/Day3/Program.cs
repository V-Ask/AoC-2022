List<(string, string)> GetRucksacks(string[] lines)
{
    List<(string, string)> output = new List<(string, string)>();
    foreach (string line in lines)
    {
        int middle = line.Length / 2;
        string compA = line.Substring(0, middle);
        string compB = line.Substring(middle);
        output.Add((compA, compB));
    }
    return output;
}

List<char> GetCommonGroups(string[] lines)
{
    List<char> output = new List<char>();
    string[] group = new string[3];
    for(int i = 0; i < lines.Length + 1; i++)
    {
        if (i % 3 == 0 && i != 0)
        {
            output.Add(commonGroupChar(group));
            group = new string[3];
            if(i < lines.Length) group[0] = lines[i];
        }
        else group[i % 3] = lines[i];
    }
    return output;
}

char commonGroupChar(string[] group)
{
    return group[0].Intersect(group[1]).Intersect(group[2]).First();
}

char commonChar(string compA, string compB)
{
    return compA.Intersect(compB).First();
}

int getScore((string, string) rucksack)
{
    char common = commonChar(rucksack.Item1, rucksack.Item2);
    return getPriority(common);
}

int getBadgeScore(List<char> badges)
{
    return badges.Select((x) => getPriority(x)).Sum();
}

int getPriority(char character)
{
    return (char.IsUpper(character) ? 26 : 0) + character % 32;
}

int getTotalScore(List<(string, string)> rucksacks)
{
    return rucksacks.Select((x) => getScore(x)).Sum();
}

int getTotalBadgeScore(string[] lines)
{
    return (getBadgeScore(GetCommonGroups(lines)));
}

string[] lines = File.ReadAllLines(@"D:\AdventOfCode\2022\Day3\input.txt");
List<(string, string)> rucksacks = GetRucksacks(lines);
Console.WriteLine("Part I: " + getTotalScore(rucksacks));
Console.WriteLine("Part 2: " + getTotalBadgeScore(lines));
Console.ReadLine();