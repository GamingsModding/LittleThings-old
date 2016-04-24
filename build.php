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
if (count($new_version) == 3) {
    $file = fopen("version/mc1.9.txt", "w");
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

    echo "Building LittleThings V" + implode(".", $new_version) + "\n";
    exec("./gradlew build");

    echo "\nCommiting\n";

    exec("git commit -a -m \"Build V" . implode(".", $new_version) . "\"");

    echo "\n Build Complete!\n";
}