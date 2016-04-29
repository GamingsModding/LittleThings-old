<?php
array_shift($argv);
$current_version = explode(".", file_get_contents("version/mc1.9.txt"));
$new_version = [];
switch (array_shift($argv)) {
    case "release":
        $new_version[0] = $current_version[0] + 1;
        $new_version[1] = 0;
        $new_version[2] = 0;
        break;
    case "major":
        $new_version[0] = $current_version[0];
        $new_version[1] = $current_version[1] + 1;
        $new_version[2] = 0;
        break;
    case "minor":
        $new_version[0] = $current_version[0];
        $new_version[1] = $current_version[1];
        $new_version[2] = $current_version[2] + 1;
        break;
    default:
        echo "Release, Major or Minor Required\n";
}

$file_name = null;

if (array_shift($argv) == "alpha") {
    $file_name = "mc1.9a.txt";
} else {
    $file_name = "mc1.9.txt"
}

if ($file_name != null && count($new_version) == 3) {
    $file = fopen("version/" . $file_name, "w");
    fwrite($file, implode(".", $new_version));
    fclose($file);

    $buildProp = explode(PHP_EOL, file_get_contents("build.properties"));
    $i = 0;
    foreach ($buildProp as $prop) {
        $prop = explode("=", $prop);
        if ($prop[0] == "version") {
            $prop[1] = implode(".", $new_version);
        }
        $buildProp[$i] = implode("=", $prop);
        $i++;
    }
    $file = fopen("build.properties", "w");
    fwrite($file, implode(PHP_EOL, $buildProp));
    fclose($file);

    exec("./gradlew build");
    echo "\nCommiting\n";
    exec("git commit -a -m \"Build V" . implode(".", $new_version) . "\"");
    exec("git push");
    echo "\n Build Complete!\n";
}