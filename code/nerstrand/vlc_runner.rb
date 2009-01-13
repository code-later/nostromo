#!/usr/bin/env ruby

`/Applications/VLC.app/Contents/MacOS/VLC #{ARGV[0]} --sout='#transcode{vcodec=mp4v,acodec=mpga,vb=800,ab=128,deinterlace}:rtp{dst=192.168.1.2,port=1234,sdp=#{ARGV[1]}}'`