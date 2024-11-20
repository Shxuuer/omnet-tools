source ../setenv
cd ../workspace/inet4.5/motivation
opp_run -m -u Cmdenv -n "../cbs_tas_test:../cbs_test:../examples:../showcases:../src:../tests/networks:../tests/validation:../tutorials" -x "inet.common.selfdoc;inet.emulation;inet.showcases.visualizer.osg;inet.examples.emulation;inet.showcases.emulation;inet.transportlayer.tcp_lwip;inet.applications.voipstream;inet.visualizer.osg;inet.examples.voipstream" --image-path=../images -l ../src/INET omnetpp.ini
